(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('SecondaryEvaluation', SecondaryEvaluation);

    SecondaryEvaluation.$inject = ['$resource'];

    function SecondaryEvaluation ($resource) {
        var resourceUrl =  'api/secondary-evaluations/:id';

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
