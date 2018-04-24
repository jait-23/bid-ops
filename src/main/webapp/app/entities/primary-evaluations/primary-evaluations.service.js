(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('PrimaryEvaluations', PrimaryEvaluations);

    PrimaryEvaluations.$inject = ['$resource'];

    function PrimaryEvaluations ($resource) {
        var resourceUrl =  'api/primary-evaluations/:id';

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
