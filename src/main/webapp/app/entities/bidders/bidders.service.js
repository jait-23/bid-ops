(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('Bidders', Bidders);

    Bidders.$inject = ['$resource'];

    function Bidders ($resource) {
        var resourceUrl =  'api/bidders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
