(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('PrimaryEvaluationsDetailController', PrimaryEvaluationsDetailController);

    PrimaryEvaluationsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PrimaryEvaluations', 'Solicitations'];

    function PrimaryEvaluationsDetailController($scope, $rootScope, $stateParams, previousState, entity, PrimaryEvaluations, Solicitations) {
        var vm = this;
        
        vm.solicitations = entity;
        
        vm.primaryEvaluations = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:primaryEvaluationsUpdate', function(event, result) {
            vm.primaryEvaluations = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
