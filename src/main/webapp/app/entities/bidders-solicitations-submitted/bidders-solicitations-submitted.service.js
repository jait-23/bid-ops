(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('BiddersSolicitationsSubmitted', BiddersSolicitationsSubmitted);

    BiddersSolicitationsSubmitted.$inject = ['$resource'];

    function BiddersSolicitationsSubmitted ($resource) {
        var resourceUrl =  'api/bidders-solicitations-submitteds/:id';

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