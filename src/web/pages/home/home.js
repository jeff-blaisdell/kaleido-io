(function() {

    'use strict';

    require('angular');
    require('kaleido-templates');
    require('./home.html');

    var module = angular.module('kaleido.home.app', [
    ]);

    module.controller('HomeCtrl', function ($scope, $log) {
        $log.info('Home controller has loaded.');
    });

})();
