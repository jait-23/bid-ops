(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('SolicitationAuthor', SolicitationAuthor);

    SolicitationAuthor.$inject = ['$resource'];

    function SolicitationAuthor ($resource) {
        var resourceUrl =  'api/solicitation-authors/:id';

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
