(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('PrimaryEvaluationsDetailController', PrimaryEvaluationsDetailController);

    PrimaryEvaluationsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', '$uibModal', '$http', 'previousState', 'entity', 'PrimaryEvaluations', 'Solicitations'];

    function PrimaryEvaluationsDetailController($scope, $rootScope, $stateParams, $uibModal, $http, previousState, entity, PrimaryEvaluations, Solicitations) {
        var vm = this;
        vm.editForm;
        
        vm.solicitations = entity;
        
        vm.primaryEvaluations = entity;
        console.log(vm.primaryEvaluations);
        vm.previousState = previousState.name;
        
        var onSaveFinished = function (result) {
        	console.log("done on finsihed" + result);
        };
        
        $scope.save = function() {
			var updatedPrimaryEvaluations = {};
			angular.copy(vm.primaryEvaluations, updatedPrimaryEvaluations);
			
			if (vm.primaryEvaluations.id != null) {
				console.log(vm.primaryEvaluations.id);
				PrimaryEvaluations.update(updatedPrimaryEvaluations, onSaveFinished);
        	} else { 
        		PrimaryEvaluations.save(vm.primaryEvaluations, onSaveFinished);
        	}
        };
        
        $scope.saveApprove = function() {
			var updatedPrimaryEvaluations = {};
			angular.copy(vm.primaryEvaluations, updatedPrimaryEvaluations);
			
			if (vm.primaryEvaluations.id != null) {
				console.log(vm.primaryEvaluations.id);
				vm.primaryEvaluations["status"] = 'approve';
				PrimaryEvaluations.update(updatedPrimaryEvaluations, onSaveFinished);
        	} else { 
        		PrimaryEvaluations.save(vm.primaryEvaluations, onSaveFinished);
        	}
        };
        
        $scope.saveReject = function() {
			var updatedPrimaryEvaluations = {};
			angular.copy(vm.primaryEvaluations, updatedPrimaryEvaluations);
			
			if (vm.primaryEvaluations.id != null) {
				console.log(vm.primaryEvaluations.id);
				vm.primaryEvaluations["status"] = 'reject';
				PrimaryEvaluations.update(updatedPrimaryEvaluations, onSaveFinished);
        	} else { 
        		PrimaryEvaluations.save(vm.primaryEvaluations, onSaveFinished);
        	}
        };
        
        $scope.approveDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/primary-evaluations/primary-evaluations-approve-dialog.html',
				controller : 'ApproveDialogSaveController',
				scope : $scope,
				size : 'lg'
			});
		}
		
		$scope.rejectDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/primary-evaluations/primary-evaluations-reject-dialog.html',
				controller : 'RejectDialogSaveController',
				scope : $scope,
				size : 'lg'
			});
		}
		
		 $scope.tabs = [{
	            title: 'Details',
	            url: 'details.tpl.html'
	        }];

        var unsubscribe = $rootScope.$on('bidopscoreApp:primaryEvaluationsUpdate', function(event, result) {
            vm.primaryEvaluations = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
