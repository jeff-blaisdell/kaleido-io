(function() {

    'use strict';
    require('angular');
    require('angular-resource');
    require('base-resource');

    var module = angular.module('kaleido.resources.auth', [
        'kaleido.resources.base'
    ]);

    module.factory('Auth', function($q, $log, appContextPath) {
        return {
            authenticate: function (username, password) {
                var deferred = $q.defer();

                $.ajax({
                    type: 'POST',
                    url: appContextPath + '/app/auth?client_name=BasicAuthClient',
                    data: {},
                    dataType: 'json',
                    headers: {
                        "Authorization": "Basic " + btoa(username + ":" + password)
                    }
                }).done(function() {
                    $log.debug('User has been authenticated.');
                    deferred.resolve();
                }).fail(function() {
                    $log.error('Unable to authenticate user.');
                    deferred.reject();
                });

                return deferred.promise;
            }
        };

    });

})();
