(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationsDetailController', SolicitationsDetailController);

    SolicitationsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', '$uibModal', 'previousState', 'entity', 'Solicitations'];

    function SolicitationsDetailController($scope, $rootScope, $stateParams, $uibModal, previousState, entity, Solicitations) {
        var vm = this;

        vm.solicitations = entity;
        vm.previousState = previousState.name;
        
        $scope.save = function() {
			var updatedSolicitations = {};
			angular.copy(vm.solicitations, updatedSolicitations);
			if (vm.solicitations.id != null) {
				var uploadImg = document.getElementById("uploadPDF");
				if(uploadImg != null){
					if(uploadImg.files.length == 1){
						vm.solicitations["organizationId"] = $rootScope.orgId;
						$scope.uploadFile();
					}
					else{
						if(updatedSolicitations.requiredDocuments != null){
							updatedSolicitations.requiredDocuments = JSON.stringify(updatedSolicitations.requiredDocuments);
						}
						else{
							updatedSolicitations.requiredDocuments = null;
						}
						Solicitations.update({orgId: $rootScope.orgId, id: updatedSolicitations.id}, updatedSolicitations, onSaveFinished);
					}
				}
				else{
					vm.solicitations["organizationId"] = $rootScope.orgId;
					if(updatedSolicitations.requiredDocuments != null)
					{
						updatedSolicitations.requiredDocuments = JSON.stringify(updatedSolicitations.requiredDocuments);
					}
					else{
						updatedSolicitations.requiredDocuments = null;	
					}
					Solicitations.update({orgId: $rootScope.orgId, id: updatedSolicitations.id}, updatedSolicitations, onSaveFinished);
				}
			}
			else {
				vm.solicitations["organizationId"] = $rootScope.orgId;
				if(vm.solicitations.requiredDocuments != null)
				{
					vm.solicitations.requiredDocuments = JSON.stringify(vm.solicitations.requiredDocuments);
				}
				else{
					vm.solicitations.requiredDocuments = null;	
				}
				Solicitations.save(vm.solicitations, onSaveFinished);
			}
		};
		
		$scope.saveExtraInfo = function(key, value) {
			if (vm.solicitations.requiredDocuments == null) {
				vm.solicitations.requiredDocuments = {};
			}
			vm.solicitations.requiredDocuments[key] = value;
			$scope.save();
		};
		
		
		$scope.deleteExtraInfo = function(key, value) {
			vm.solicitations.requiredDocuments[key] = value;
			$scope.save();

			var updatedSolicitations = {};
			angular.copy(vm.solicitations, updatedSolicitations);
				updatedSolicitations.requiredDocuments.platform = "";

			angular.copy(updatedSolicitations, vm.solicitations);
			$scope.save();

		};
		
		$scope.uploadFile = function() {
			var fd = new FormData();

			var file = document.getElementById("uploadPDF").files[0];
			if (file != null) {
				fd.append('file', file);
				var locationUrl = "swagger-ui/"
									+ file.name;
				console.log("the saved location of the file is: "
													+ locationUrl);
				var filetype = file.type;

				var type = filetype
							.substring(filetype.indexOf("/") + 1);

				var uploadUrl = 'file/upload';

				$http({
					url : uploadUrl,
					method : 'POST',
					data : fd,
					withCredentials : true,
					headers : {
						'Content-Type' : undefined
					},
					transformRequest : angular.identity,
					mimeType : "multipart/form-data",
					contentType : false,
					cache : false,
					processData : false
				})
				.then(
					function s(response) {
						console.log("Post Success");
						vm.solicitations.requiredDocumentsRef = locationUrl;
						if(vm.solicitations.requiredDocuments != null)
						{
							vm.solicitations.requiredDocuments = JSON.stringify(vm.solicitations.requiredDocuments);
						}
						else{
						vm.solicitations["requiredDocuments"] = null;	
						}
						Solicitations.update({orgId: $rootScope.orgId, id: vm.solicitations.id}, vm.solicitations,onSaveFinished);
					}, function e(response) {
						console.log("Post failure");
				});
			}
		};
    
		$scope.requiredDocumentsInfoCreateModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-extraInfo-create-dialog.html',
				controller : 'SolicitationsExtraInfoCreateDialogController',
				scope : $scope,
				size : 'lg'
			});
		}

		$scope.requiredDocumentsInfoEditModal = function(key) {
			$scope.editKey = key;
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-extraInfo-edit-dialog.html',
				controller : 'SolicitationsExtraInfoEditDialogController',
				scope : $scope,
				size : 'lg'
			});
		}

		$scope.requiredDocumentsInfoDeleteModal = function(itemkey, itemvalue) {
			$scope.key = itemkey;
			$scope.value = itemvalue;
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-extraInfo-delete-dialog.html',
				controller : 'SolicitationsExtraInfoDeleteDialogController',
				scope : $scope,
				size : 'lg'
			});
		}
	
		$scope.saveDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-details-save-dialog.html',
				controller : 'SolicitationsDialogDetailsSaveController',
				scope: $scope,
				size : 'lg'
			});
		}
		
		$scope.cancelDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-details-cancel-dialog.html',
				controller : 'SolicitationsDetailsCancelDialogController',
				controllerAs: 'vm',
				size : 'lg'
			});
		}

        var unsubscribe = $rootScope.$on('bidopscoreApp:solicitationsUpdate', function(event, result) {
            vm.solicitations = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        $scope.tabs = [{
            title: 'Details',
            url: 'details.tpl.html'
        }, {
            title: 'Upload Documents',
            url: 'Documents.tpl.html'
        }];

    $scope.currentTab = 'details.tpl.html';

    $scope.onClickTab = function (tab) {
        $scope.currentTab = tab.url;
    }
    
    $scope.isActiveTab = function(tabUrl) {
        return tabUrl == $scope.currentTab;
    }

    
    }
})();
