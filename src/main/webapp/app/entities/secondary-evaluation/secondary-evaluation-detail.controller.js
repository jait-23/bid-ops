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
        
        console.log(vm.secondaryEvaluation);
        var total=0;
        var count=0;
        for(i=0; i<vm.secondaryEvaluation.length[i]; i++) {
        	if(vm.secondaryEvaluation[i].score!="null" && vm.secondaryEvaluation[i].score!=undefined)
        		{
        	      total+=vm.secondaryEvaluation[i].score;
        	      count++;
        		}
        }
        
        for(i=0; i<vm.secondaryEvaluation.length[i]; i++){
        	
        }

        var unsubscribe = $rootScope.$on('bidopscoreApp:secondaryEvaluationUpdate', function(event, result) {
            vm.secondaryEvaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
