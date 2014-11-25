describe('kaleido.resources.base', function() {

    'use strict';
    require('base-resource');

    beforeEach(angular.mock.module('kaleido.resources.base'));

    it('adds a date de-serialization function to $httpProvider', inject(function($http) {
        expect(_.any($http.defaults.transformResponse, function(func) {
            return _.contains(func.toString(), 'deserializeDates');
        })).toBeTruthy();
    }));


    it('adds a date serialization function to $httpProvider', inject(function($http) {
        expect(_.any($http.defaults.transformRequest, function(func) {
            return _.contains(func.toString(), 'serializeDates');
        })).toBeTruthy();
    }));

});
