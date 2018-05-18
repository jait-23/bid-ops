'use strict';

angular.module('bidopscoreApp').controller(
		'SecondaryEvaluationDialogDetailsSaveController',
		[
			'$scope',
			'$stateParams',
			'$uibModalInstance',
			'Bidders',
		function($scope, $stateParams, $uibModalInstance, Bidders) {

			$scope.saveConfirmed = function() {
				console.log("m herre");
				$scope.save();
				$uibModalInstance.close();
			}
			
			$scope.clear = function() {
				$uibModalInstance.dismiss('cancel');
			};
		}]);