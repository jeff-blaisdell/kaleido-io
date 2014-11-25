
(function() {

    'use strict';

    require('angular');
    require('angular-ui-router');
    require('kaleido-templates');
    require('./register-form.html');
    require('user-resource');
    require('auth-resource');

    var module = angular.module('kaleido.components.register-form', [
        'kaleido.templates',
        'kaleido.resources.user',
        'kaleido.resources.auth'
    ]);

    module.service('registerService', function() {

    });

    module.controller('RegisterFormCtrl', function(
        $scope,
        $state,
        $log,
        User,
        Auth
    ) {
        $scope.user = new User();
        $scope.form = {
            submitted: false,
            submitting: false,
            passwordsMatch: true
        };

        $scope.clearFormState = function() {
            $scope.form.submitted = false;
            $scope.form.passwordsMatch = true;
        };

        $scope.isFormValid = function() {
            $scope.form.passwordsMatch = ($scope.user.password === $scope.user.confirmPassword);
            return ($scope.form.passwordsMatch);
        };

        $scope.register = function() {
            $scope.form.submitted = true;
            $scope.form.submitting = true;
            if ($scope.isFormValid()) {
                var username = $scope.user.email;
                var password = $scope.user.password;
                $scope.user.$save().then(
                    function onSuccess(user) {
                        $log.info('Success!', user);
                        Auth.authenticate(username, password).then(
                            function onSuccess() {
                                $state.go('home');
                            },
                            function onError(resp) {

                            }
                        );
                    },
                    function onError(resp) {
                        $log.error('Failure...', resp);
                    }
                );
            }
            $scope.form.submitting = false;
        };

    });

    module.directive('registerForm', function() {
        return {
            restrict: 'E',
            replace: true,
            scope: {},
            controller: 'RegisterFormCtrl',
            templateUrl: 'components/register-form/register-form.html',
            require: 'form',
            link: function(scope, element, attrs, formCtrl) {

                scope.$watch('user', function() {
                    scope.clearFormState();
                }, true);

                scope.$watch('form.passwordsMatch', function(passwordsMatch) {
                    formCtrl.password.$setValidity('passwordsMatch', passwordsMatch);
                    formCtrl.confirmPassword.$setValidity('passwordsMatch', passwordsMatch);
                });
            }
        };
    });

})();
