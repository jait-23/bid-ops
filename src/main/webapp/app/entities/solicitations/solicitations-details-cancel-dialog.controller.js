'use strict';

angular.module('bidopscoreApp').controller(
		'SolicitationsDetailsCancelDialogController',
		[
			'$state',
			'$scope',
			'$stateParams',
			'$uibModalInstance',
			'Solicitations',
		function($state, $scope, $stateParams, $uibModalInstance, Solicitations) {

			var vm = this;

			$scope.clear = function() {
				$uibModalInstance.dismiss('cancel');
			};
			$scope.cancel = function() {
				$uibModalInstance.dismiss('cancel');
				$state.go('solicitations');
			};
		}]);
