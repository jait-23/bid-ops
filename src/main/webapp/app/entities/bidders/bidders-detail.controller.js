(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('BiddersDetailController', BiddersDetailController);

    BiddersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Bidders', 'Solicitations', 'Jhi_user'];

    function BiddersDetailController($scope, $rootScope, $stateParams, previousState, entity, Bidders, Solicitations, Jhi_user) {
        var vm = this;

        vm.bidders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:biddersUpdate', function(event, result) {
            vm.bidders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
