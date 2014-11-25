(function() {

    'use strict';

    require('angular');
    require('angular-ui-router');
    require('kaleido-templates');
    require('user-resource');
    require('./signin.html');

    var module = angular.module('kaleido.signin.app', [
        'kaleido.resources.auth'
    ]);

    module.controller('SigninCtrl', function ($scope, $state, $log, Auth) {
        $scope.user = {};

        $scope.signin = function() {
            Auth.authenticate($scope.user.email, $scope.user.password).then(
                function onSuccess() {
                    $state.go('home');
                },
                function onError(resp) {

                }
            );

        };

        $log.info('Signin controller has loaded.');
    });

})();
