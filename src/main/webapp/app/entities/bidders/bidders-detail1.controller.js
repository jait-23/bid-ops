(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('BiddersDetail1Controller', BiddersDetail1Controller);

    BiddersDetail1Controller.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Bidders'];

    function BiddersDetail1Controller($scope, $rootScope, $stateParams, previousState, entity, Bidders) {
        var vm = this;

        vm.bidders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:biddersUpdate', function(event, result) {
            vm.bidders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
