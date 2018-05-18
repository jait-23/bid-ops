(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('BiddersDetailController', BiddersDetailController);

    BiddersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Bidders', 'Solicitations'];

    function BiddersDetailController($scope, $rootScope, $stateParams, previousState, entity, Bidders, Solicitations) {
        var vm = this;

        vm.bidders = entity;
        vm.previousState = previousState.name;
        vm.solicitations = Solicitations.query();
        console.log(vm.solicitations + "m here ")

        var unsubscribe = $rootScope.$on('bidopscoreApp:biddersUpdate', function(event, result) {
            vm.bidders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
