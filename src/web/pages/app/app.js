(function() {

    'use strict';

    require('angular');
    require('angular-ui-router');
    require('bootstrap');
    require('../home/home');
    require('../register/register');
    require('../signin/signin');

    var module = angular.module('kaleido.app', [
        'ui.router',
        'kaleido.home.app',
        'kaleido.register.app',
        'kaleido.signin.app',
        'kaleido.templates'
    ]);

    module.config(function($stateProvider, $urlRouterProvider, $locationProvider) {

        $locationProvider.html5Mode(true);
        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('home', {
                url: '/',
                controller: 'HomeCtrl',
                templateUrl: 'pages/home/home.html'
            })
            .state('signin', {
                url: '/app/auth/signin',
                controller: 'SigninCtrl',
                templateUrl: 'pages/signin/signin.html'
            })
            .state('register', {
                url: '/app/auth/register',
                controller: 'RegisterCtrl',
                templateUrl: 'pages/register/register.html'
            });

    });

    angular.element(document).ready(function() {
        angular.bootstrap(document, ['kaleido.app']);
    });

})();

