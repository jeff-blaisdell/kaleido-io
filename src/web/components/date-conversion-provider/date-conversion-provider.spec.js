describe('kaleido.providers.date-conversion', function() {

    'use strict';
    require('date-conversion-provider');

    beforeEach(angular.mock.module('kaleido.providers.date-conversion'));

    function getData() {
        return [
            {
                adminCostMonth: 0.42,
                adminCostYear: 5,
                amountFromDate: "2014-05-07T00:00:00.000Z",
                amountToDate: "9999-12-31T00:00:00.000Z",
                notCorrectDateKeyName: "2014-05-07T00:00:00.000Z",
                arrayList: [
                    {
                        name: 'test',
                        arrayListDate: "2014-05-07T00:00:00.000Z"
                    },
                    {
                        name: 'test'
                    }
                ],
                object: {
                    name: 'object',
                    objectDate: "2014-05-07T00:00:00.000Z",
                    objectArrayList: [
                        {
                            test: 'test',
                            objectArrayListDate: "2014-05-07T00:00:00.000Z"
                        }
                    ]
                }
            }
        ];
    }

    it('should deserialize date strings to ISO dates on an object.', inject(function(dateConversion) {
        var data = getData();
        dateConversion.deserializeDates(data);
        expect(_.isDate(data[0].amountFromDate)).toBeTruthy();
        expect(_.isDate(data[0].amountToDate)).toBeTruthy();
        expect(_.isDate(data[0].notCorrectDateKeyName)).toBeFalsy();
        expect(_.isDate(data[0].arrayList[0].arrayListDate)).toBeTruthy();
        expect(_.isDate(data[0].object.objectDate)).toBeTruthy();
        expect(_.isDate(data[0].object.objectArrayList[0].objectArrayListDate)).toBeTruthy();

        // check specific date fields
        expect(data[0].amountFromDate.getUTCFullYear()).toBe(2014);
        expect(data[0].amountFromDate.getUTCDate()).toBe(7);
        expect(data[0].amountFromDate.getUTCMonth()).toBe(4);
    }));

    it('should serialize date objects to ISO local-date-time strings.', inject(function(dateConversion) {
        var data                = getData();
        var amountFromDate      = data[0].amountFromDate.replace('Z', '');
        var amountToDate        = data[0].amountToDate.replace('Z', '');
        var arrayListDate       = data[0].arrayList[0].arrayListDate.replace('Z', '');
        var objectDate          = data[0].object.objectDate.replace('Z', '');
        var objectArrayListDate = data[0].object.objectArrayList[0].objectArrayListDate.replace('Z', '');
        dateConversion.deserializeDates(data);
        dateConversion.serializeDates(data);
        expect(data[0].amountFromDate).toEqual(amountFromDate);
        expect(data[0].amountToDate).toEqual(amountToDate);
        expect(data[0].arrayList[0].arrayListDate).toEqual(arrayListDate);
        expect(data[0].object.objectDate).toEqual(objectDate);
        expect(data[0].object.objectArrayList[0].objectArrayListDate).toEqual(objectArrayListDate);
    }));
});
