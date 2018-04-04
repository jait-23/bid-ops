'use strict';

angular.module('bidopscoreApp').controller(
		'SolicitationsDeleteController',
		[
				'$scope',
				'$stateParams',
				'$modalInstance',
				'Solicitations',
				function($scope, $stateParams, $modalInstance, entity,
						Solicitations, ManualItem) {

					$scope.deleteFinished = function() {
						var deleteManual = ManualItem;
						$scope.deleteManuals(deleteManual);
					}

					$scope.saveConfirmed = function() {
						$scope.save();
					}

				} ]);