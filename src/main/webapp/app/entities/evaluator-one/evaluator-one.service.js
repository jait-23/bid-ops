(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('EvaluatorOne', EvaluatorOne);

    EvaluatorOne.$inject = ['$resource'];

    function EvaluatorOne ($resource) {
        var resourceUrl =  'api/evaluator-ones/:id';

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
