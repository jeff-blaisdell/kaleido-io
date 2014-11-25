var webpack = require('webpack');
var path    = require('path');
require('html-loader');
require('style-loader');
require('raw-loader');
require('jshint-loader');

module.exports = {
    entry: {
        app: './src/web/pages/app/app.js'
    },
    output: {
        filename: '[name].js',
        sourceMapFilename: '[file].map',
        path: 'src/ratpack/public/scripts'
    },
    module: {
        preLoaders: [
            {
                test: /\.js$/, // include .js files
                exclude: /node_modules/, // exclude any and all files in the node_modules folder
                loader: "jshint-loader"
            }
        ],
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
    resolve: {
        modulesDirectories: ['web_modules', 'node_modules', 'components']
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            'windows.jQuery': 'jquery',
            _: 'lodash'
        })
    ],
    jshint: {
        quotmark: false,
        camelcase: false,
        curly:   true,
        eqeqeq:  true,
        immed:   true,
        latedef: true,
        newcap:  true,
        noarg:   true,
        sub:     true,
        undef:   true,
        boss:    true,
        eqnull:  true,
        unused:  false,
        browser: true,
        strict:  true,
        jquery:  true,
        emitErrors: true,
        failOnHint: true,
        globals: {
            _: true,
            $: true,
            jQuery: true,
            angular: true
        }
    }
};
