var gulp          = require('gulp');
var gutil         = require('gulp-util');
var $             = require('gulp-load-plugins')();
var webpack       = require('webpack');
var ngminPlugin   = require('ngmin-webpack-plugin');
var _             = require('lodash');
var del           = require('del');
var runSequence   = require('run-sequence');
var webpackConfig = require('./webpack.config');
var karma         = require('karma').server;
var argv          = require('yargs').argv;

var project = {
    styleSource: [
        'src/web/base/**/*.scss',
        'src/web/components/**/*.scss',
        'src/web/pages/**/*.scss'
    ],
    styleOutputDir: 'src/ratpack/public/styles',
    env: {
       development: 'DEV',
       production: 'PRD'
    },
    karma: {
        configFile: __dirname + '/karma.conf.js',
        singleRun: true
    }
};

/**
 * Method to configure web pack for being run in development mode.
 * @param config
 */
function configureWebPack(config) {
    config.devtool = false;
    config.plugins = config.plugins.concat(
        new ngminPlugin(),
        new webpack.optimize.DedupePlugin(),
        new webpack.optimize.UglifyJsPlugin({
            compress: { warnings: false },
            sourceMap: true
        })
    );
}

/**
 * Method to configure web pack for being run in production mode.
 * @param config
 */
function configureWebPackDev(config) {
    config.devtool = 'sourcemap';
    config.debug   = true;
}

var WebPackCompiler = (function() {
    var compiler = {};

    function createInstance(env) {
        var config = Object.create(webpackConfig);
        (env === project.env.production ? configureWebPack(config) : configureWebPackDev(config))
        return webpack(config);
    }

    return {
        getInstance: function(env) {
            if (!compiler[env]) {
                compiler[env] = createInstance(env);
            }
            return compiler[env];
        }
    };
})();

gulp.task('default', function () {
    runSequence(
        ['build']
    );
});

gulp.task('build', ['clean'], function (callback) {
    runSequence(
        'test',
        ['webpack', 'sass'],
        callback
    );
});

gulp.task('build:dev', ['clean', 'webpack:dev', 'sass'], function () {
    gulp.watch(['src/web/**/*.{js,html}'], ['webpack:dev']);
    gulp.watch(project.styleSource, ['sass']);
});

gulp.task('clean', function(cb) {
    del([webpackConfig.output.path, project.styleOutputDir], cb);
});

gulp.task('webpack', function (callback) {
    var compiler = WebPackCompiler.getInstance(project.env.production);
    compiler.run(function(err, stats) {
        if(err)  {
            throw new gutil.PluginError('webpack', err);
        }
        gutil.log('[webpack]', stats.toString({
            colors: true
        }));
        callback();
    });
});

gulp.task('webpack:dev', function (callback) {
    var compiler = WebPackCompiler.getInstance(project.env.development);
    compiler.run(function(err, stats) {
        if(err) {
            throw new gutil.PluginError('webpack:dev', err);
        }
        gutil.log('[webpack:build-dev]', stats.toString({
            colors: true
        }));

        callback();
    })
});

gulp.task('sass', function () {
    gulp.src(project.styleSource)
        .pipe($.sass({
            includePaths: [
                'node_modules/bootstrap-sass/assets/stylesheets',
                'src/web/base/common'
            ]
        }))
        .pipe($.concat('app.css'))
        .pipe(gulp.dest(project.styleOutputDir));
});


gulp.task('test', function(callback) {
    var browsers = (argv.browsers ? argv.browsers.split(',') : undefined);
    var conf = Object.create(project.karma);
    if (browsers) {
        conf.browsers = browsers
    }

    karma.start(conf, callback);
});
