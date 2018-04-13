(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('BiddersSolicitationsSubmittedDetailController', BiddersSolicitationsSubmittedDetailController);

    BiddersSolicitationsSubmittedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BiddersSolicitationsSubmitted'];

    function BiddersSolicitationsSubmittedDetailController($scope, $rootScope, $stateParams, previousState, entity, BiddersSolicitationsSubmitted) {
        var vm = this;

        vm.biddersSolicitationsSubmitted = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:biddersSolicitationsSubmittedUpdate', function(event, result) {
            vm.biddersSolicitationsSubmitted = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
