(function() {

    'use strict';

    require('angular');
    require('date-conversion-provider');

    var module = angular.module('kaleido.resources.base', [
        'kaleido.providers.date-conversion'
    ]);

    module.constant('appContextPath', window.kaleido ? (window.kaleido.contextPath || '') : '');

    module.config(function ($httpProvider, dateConversionProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $httpProvider.defaults.transformResponse.push(function transFormDates(responseData) {
            dateConversionProvider.deserializeDates(responseData);
            return responseData;
        });

        $httpProvider.defaults.transformRequest.unshift(function transFormDates(requestData) {
            dateConversionProvider.serializeDates(requestData);
            return requestData;
        });
    });


})();
