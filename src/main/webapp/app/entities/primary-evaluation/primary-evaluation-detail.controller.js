(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('PrimaryEvaluationDetailController', PrimaryEvaluationDetailController);

    PrimaryEvaluationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PrimaryEvaluation'];

    function PrimaryEvaluationDetailController($scope, $rootScope, $stateParams, previousState, entity, PrimaryEvaluation) {
        var vm = this;

        vm.primaryEvaluation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:primaryEvaluationUpdate', function(event, result) {
            vm.primaryEvaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
