(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('Solicitations', Solicitations);

    Solicitations.$inject = ['$resource', 'DateUtils'];

    function Solicitations ($resource, DateUtils) {
        var resourceUrl =  'api/solicitations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.finalFilingDate = DateUtils.convertDateTimeFromServer(data.finalFilingDate);
                        data.lastUpdated = DateUtils.convertDateTimeFromServer(data.lastUpdated);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
