(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('Files', Files);

    Files.$inject = ['$resource'];

    function Files ($resource) {
        var resourceUrl =  'api/files/:id';

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
