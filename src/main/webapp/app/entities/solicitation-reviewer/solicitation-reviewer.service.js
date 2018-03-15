(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('SolicitationReviewer', SolicitationReviewer);

    SolicitationReviewer.$inject = ['$resource'];

    function SolicitationReviewer ($resource) {
        var resourceUrl =  'api/solicitation-reviewers/:id';

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
