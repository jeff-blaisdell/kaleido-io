(function() {
    'use strict';

    require('angular');

    var module = angular.module('kaleido.providers.date-conversion', []);

    module.provider('dateConversion', function DateConversionProvider() {

        var regexBloomDateField = /^.*Date$/i;
        var regexISODate = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}(?:\.\d*))(?:Z|(\+|-)([\d|:]*))?$/;

        function pad(val, length) {
            val = val + '';
            for (var i = val.length; i < length; i++) {
                val = '0' + val;
            }

            return val;
        }

        this.deserializeDates = function(object) {
            var self = this;

            if (_.isArray(object)) {
                _.forEach(object, function(obj) {
                    this.deserializeDates(obj);
                }, self);
                return;
            } else if (!_.isObject(object)) {
                return;
            }

            _.forOwn(object, function(value, key) {
                var isKeyDateField = typeof key === "string" && key.match(regexBloomDateField);
                if (isKeyDateField &&
                    typeof value === "string" &&
                    value.match(regexISODate)) {
                    object[key] = new Date(Date.parse(value));
                } else if (_.isObject(value)) {
                    self.deserializeDates(value);
                }
            });
        };

        this.serializeDates = function(object) {
            var self = this;

            if (_.isArray(object)) {
                _.forEach(object, function(obj) {
                    this.serializeDates(obj);
                }, self);
                return;
            } else if (!_.isObject(object)) {
                return;
            }

            _.forOwn(object, function(value, key) {
                var isKeyDateField = typeof key === "string" && key.match(regexBloomDateField);
                if (isKeyDateField && value instanceof Date) {
                    var month        = pad(value.getUTCMonth() + 1, 2);
                    var date         = pad(value.getUTCDate(), 2);
                    var year         = pad(value.getUTCFullYear(), 4);
                    var hour         = pad(value.getUTCHours(), 2);
                    var minute       = pad(value.getUTCMinutes(), 2);
                    var second       = pad(value.getUTCSeconds(), 2);
                    var millisecond  = pad(value.getUTCMilliseconds(), 3);
                    var dateString   = year + '-' + month + '-' + date + 'T' + hour + ':' + minute + ':' + second + '.' + millisecond;

                    object[key] = dateString;
                } else if (_.isObject(value)) {
                    self.serializeDates(value);
                }
            });
        };

        this.$get = function DateConversionFactory() {
            return this;
        };
    });
})();
