(function() {

    'use strict';
    require('angular');
    require('angular-resource');
    require('base-resource');

    var module = angular.module('kaleido.resources.user', [
        'ngResource',
        'kaleido.resources.base'
    ]);

    module.factory('User',
        function(
            $resource,
            appContextPath
        ) {
            return $resource(appContextPath + '/api/user/:userGuid',
                {
                    userGuid: '@userGuid'
                },
                {
                    update: {
                        method: 'POST'
                    }
                }
            );
        }
    );

})();
