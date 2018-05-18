(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationReviewerDetailController', SolicitationReviewerDetailController);

    SolicitationReviewerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SolicitationReviewer', 'Solicitations'];

    function SolicitationReviewerDetailController($scope, $rootScope, $stateParams, $uibModal, $http, previousState, entity, SolicitationReviewer, Solicitations) {
        var vm = this;
        vm.editForm;

        vm.solicitationReviewer = entity;
        vm.previousState = previousState.name;
        $scope.extraInfo = vm.solicitationReviewer.requiredDocuments;
        
        //vm.solicitations = Solicitations.query();
        
        var unsubscribe = $rootScope.$on('bidopscoreApp:solicitationsUpdate', function(event, result) {
            vm.solicitationReviewer = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        var onSaveFinished = function (result) {
        	console.log("done on finsihed");
        };
        
        $scope.solicitationReviewer = vm.solicitationReviewer.requiredDocuments;
        
        $scope.save = function() {
			var updatedSolicitations = {};
			angular.copy(vm.solicitationReviewer, updatedSolicitations);
			updatedSolicitations.requiredDocuments = JSON.stringify(updatedSolicitations.requiredDocuments);
			
			if (vm.solicitationReviewer.id != null) {
				Solicitations.update(updatedSolicitations, onSaveFinished);
        	} else { 
        		Solicitations.save(vm.solicitationReviewer, onSaveFinished);
        	}
        };
        
        $scope.uploadFile = function () {
            var fd = new FormData();
            
            var file = document.getElementById("uploadPDF").files[0];
           if(file != null)
           {  
	            fd.append('file', file);
	           var  locationUrl = "http://localhost:8080/swagger-ui/" + file.name;
	          
	            var filetype = file.type;
	                            
	           var type = filetype.substring(filetype.indexOf("/") + 1);
	            
	            var uploadUrl = 'file/upload';
	                       
	            $http({
	                url: uploadUrl,
	                method : 'POST',
	                data:  fd,
	                withCredentials: true,
	                headers: {'Content-Type': undefined },
	                transformRequest: angular.identity,
	                mimeType:"multipart/form-data",
	                contentType: false,
	                cache: false,
	                processData:false
	             }).then(function s(response) {console.log("Post Success"); vm.solicitationReviewer.requiredDocuments = locationUrl}, function e(response){console.log("Post failure");});
           }
        };
		
		$scope.saveExtraInfo = function(key, value) {
			if (vm.solicitationReviewer.requiredDocuments == null) {
				vm.solicitationReviewer.requiredDocuments = {};
			}
			vm.solicitationReviewer.requiredDocuments[key] = value;
			$scope.save();
		};
		
		
		$scope.deleteExtraInfo = function(key, value) {
			vm.solicitationReviewer.requiredDocuments[key] = value;
			$scope.save();

			var updatedSolicitations = {};
			angular.copy(vm.solicitationReviewer, updatedSolicitations);
				updatedSolicitations.requiredDocuments.platform = "";

			angular.copy(updatedSolicitations, vm.solicitationReviewer);
			$scope.save();

		};
		
		$scope.deleteManuals = function (item) {
        	var updatedSolicitations = {};
        	
        	angular.copy(vm.solicitationReviewer, updatedSolicitations);   
        	var index = updatedSolicitations.requiredDocuments.Manuals.indexOf(item);
        	
        	updatedSolicitations.requiredDocuments.Manuals.splice(index, 1);
        	angular.copy(updatedSolicitations, vm.solicitationReviewer);
        	 $scope.save();
        }
		
/*		$scope.uploadFile = function() {
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
		};  */
	
		$scope.saveDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-details-save-dialog.html',
				controller : 'SolicitationsDialogDetailsSaveController',
				scope : $scope,
				size : 'lg'
			});
		}
		
		$scope.cancelDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-details-cancel-dialog.html',
				controller : 'SolicitationsDetailsCancelDialogController',
				scope : $scope,
				size : 'lg'
			});
		}		
		
		$scope.requiredDocumentsInfoCreateModal = function() {        	
			$uibModal.open({
                 templateUrl: 'app/entities/solicitations/solicitationsupload-dialog.html',
                 controller: 'SolicitationsUploadDialogController',
                 scope : $scope,
                 size: 'lg'
            });
        }
		
		$scope.requiredDocumentsInfoEditModal = function() {        	
			$uibModal.open({
                 templateUrl: 'app/entities/solicitations/solicitationsedit-dialog.html',
                 controller: 'SolicitationsEditDialogController',
                 scope : $scope,
                 size: 'lg'
        	});
		}
		
		$scope.requiredDocumentsInfoDeleteModal = function(item) {        	
			$uibModal.open({
                 templateUrl: 'app/entities/solicitations/solicitationsdelete-dialog.html',
                 controller: 'SolicitationsDeleteDialogController',
                 scope : $scope,
                 size: 'lg',
                 resolve: {
                     ManualItem: function () {
                    	  console.log(item);
                       return item;
                     }
                   }
            });
        }
        
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

//    function SolicitationReviewerDetailController($scope, $rootScope, $stateParams, previousState, entity, SolicitationReviewer, Solicitations) {
//        var vm = this;
//
//        vm.solicitations = entity;
//        vm.previousState = previousState.name;
//
//        var unsubscribe = $rootScope.$on('bidopscoreApp:solicitationReviewerUpdate', function(event, result) {
//        	vm.solicitations = result;
//        });
//        $scope.$on('$destroy', unsubscribe);
//    }
//})();
