(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SecondaryEvaluationDetailController', SecondaryEvaluationDetailController);

    SecondaryEvaluationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SecondaryEvaluation'];

    function SecondaryEvaluationDetailController($scope, $rootScope, $stateParams, previousState, entity, SecondaryEvaluation) {
        var vm = this;

        vm.secondaryEvaluation = entity;
        vm.previousState = previousState.name;
        
      

        var unsubscribe = $rootScope.$on('bidopscoreApp:secondaryEvaluationUpdate', function(event, result) {
            vm.secondaryEvaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
