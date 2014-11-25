(function() {

    'use strict';

    require('angular');
    require('kaleido-templates');
    require('register-form');
    require('./register.html');

    var module = angular.module('kaleido.register.app', [
        'kaleido.components.register-form'
    ]);

    module.controller('RegisterCtrl', function ($scope, $log) {
        $log.info('Register controller has loaded.');
    });

})();
