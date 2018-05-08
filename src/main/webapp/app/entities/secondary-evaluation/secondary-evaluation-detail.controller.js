(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SecondaryEvaluationDetailController', SecondaryEvaluationDetailController);

    SecondaryEvaluationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', '$uibModal', '$http', 'previousState', 'entity', 'SecondaryEvaluation'];

    function SecondaryEvaluationDetailController($scope, $rootScope, $stateParams, $uibModal, $http, previousState, entity, SecondaryEvaluation) {
        var vm = this;
        vm.editForm;

        vm.secondaryEvaluation = entity;
        vm.previousState = previousState.name;
        
        var onSaveFinished = function (result) {
        	console.log("done on finsihed" + result);
        };
        
        $scope.save = function() {
			var updatedSecondaryEvaluations = {};
			angular.copy(vm.secondaryEvaluation, updatedSecondaryEvaluations);
			
			if (vm.secondaryEvaluation.id != null) {
				console.log(vm.secondaryEvaluation.id);
				SecondaryEvaluation.update(updatedSecondaryEvaluations, onSaveFinished);
        	} else { 
        		SecondaryEvaluation.save(vm.secondaryEvaluation, onSaveFinished);
        	}
        };
        
        $scope.updateDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/secondary-evaluation/secondary-evaluation-details-update-dialog.html',
				controller : 'SecondaryEvaluationDialogDetailsUpdateController',
				scope : $scope,
				size : 'lg'
			});
		}
		
		$scope.cancelDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/secondary-evaluation/secondary-evaluation-details-cancel-dialog.html',
				controller : 'SecondaryEvaluationDetailsCancelDialogController',
				scope : $scope,
				size : 'lg'
			});
		}
		
		$scope.tabs = [{
            title: 'Details',
            url: 'details.tpl.html'
        }];
		
		$scope.currentTab = 'details.tpl.html';

	    $scope.onClickTab = function (tab) {
	        $scope.currentTab = tab.url;
	    }
	    
	    $scope.isActiveTab = function(tabUrl) {
	        return tabUrl == $scope.currentTab;
	    }

        var unsubscribe = $rootScope.$on('bidopscoreApp:secondaryEvaluationUpdate', function(event, result) {
            vm.secondaryEvaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
