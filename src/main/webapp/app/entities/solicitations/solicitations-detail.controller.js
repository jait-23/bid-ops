(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationsDetailController', SolicitationsDetailController);

    SolicitationsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Solicitations'];

    function SolicitationsDetailController($scope, $rootScope, $stateParams, previousState, entity, Solicitations) {
        var vm = this;

        vm.solicitations = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:solicitationsUpdate', function(event, result) {
            vm.solicitations = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
