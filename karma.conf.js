var webpack = require('webpack');
var path    = require('path');
require('karma-webpack');
require('html-loader');
require('style-loader');
require('raw-loader');

module.exports = function(config) {
    config.set({

        frameworks: ['jasmine'],

        // list of files to exclude
        exclude: [],

        files: [
            // all files ending in ".spec"
            'node_modules/angular/angular.js',
            'node_modules/angular-mocks/angular-mocks.js',
            'src/web/**/*.spec.js'
            // each file acts as entry point for the webpack configuration
        ],

        preprocessors: {
            // add webpack as preprocessor
            'src/web/**/*.spec.js': ['webpack', 'sourcemap']
        },

        webpack: {
            // karma watches the test entry points
            // (you don't need to specify the entry option)
            // webpack watches dependencies
            externals: [
                {
                    angular: true,
                    'angular-mocks': true
                }
            ],

            // webpack configuration
            resolve: {
                modulesDirectories: ['web_modules', 'node_modules', 'components']
            },
            module: {
                loaders: [
                    {
                        test: /\.css$/,
                        exclude: /\.useable\.css$/,
                        loader: 'style!raw'
                    },
                    {
                        test: /\.html$/,
                        loader: 'ngtemplate?module=kaleido.templates&relativeTo=' +
                        (path.resolve(__dirname, './src/web/') + '/') + '!html'
                    }
                ]
            },
            devtool: '#inline-source-map',
            plugins: [
                new webpack.ProvidePlugin({
                    $: 'jquery',
                    jQuery: 'jquery',
                    'windows.jQuery': 'jquery',
                    _: 'lodash'
                })
            ]
        },

        // use dots reporter, as travis terminal does not support escaping sequences
        // possible values: 'dots', 'progress'
        // CLI --reporters progress
        reporters: ['junit', 'progress'],

        junitReporter: {
            outputFile: './build/test-results/jasmine-unit.xml',
            suite: 'unit'
        },

        // web server port
        // CLI --port 9876
        port: 9876,

        // enable / disable colors in the output (reporters and logs)
        colors: true,

        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        // CLI --log-level debug
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        // CLI --auto-watch --no-auto-watch
        //autoWatch: true,

        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
        // CLI --browsers Chrome,Firefox,Safari
        browsers: ['Chrome'],

        // Auto run tests on start (when browsers are captured) and exit
        // CLI --single-run --no-single-run
        singleRun: true,

        // report which specs are slower than 500ms
        // CLI --report-slower-than 500
        reportSlowerThan: 500,

        // compile coffee scripts
        //preprocessors: {
        //    '**/*.coffee': 'coffee'
        //},

        plugins: [
            'karma-jasmine',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-phantomjs-launcher',
            'karma-junit-reporter',
            'karma-webpack',
            'karma-sourcemap-loader'
        ]
    });
};
