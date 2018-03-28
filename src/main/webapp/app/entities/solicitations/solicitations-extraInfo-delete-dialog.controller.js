'use strict';

angular.module('bidopscoreApp').controller(
		'SolicitationsExtraInfoDeleteDialogController',
		[
			'$scope',
			'$stateParams',
			'$uibModalInstance',
			'Solicitations',
		function($scope, $stateParams, $uibModalInstance, Solicitations) {

			$scope.deleteFinished = function() {
				if ($scope.key !== 'Platform'
					&& $scope.key !== 'platform'
					&& $scope.key !== 'Type'
					&& $scope.key !== 'type') {
						delete $scope.solicitations[$scope.key];
					} else {
						$scope.solicitations[$scope.key] = null;
					}
				$scope.save();
				$uibModalInstance.close();
			}
			$scope.cancel = function(){
				$uibModalInstance.close();
			}
		}]);
