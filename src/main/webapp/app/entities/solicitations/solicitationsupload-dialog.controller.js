'use strict';
angular
		.module('bidopscoreApp')
		.controller(
				'SolicitationsUploadDialogController',
				[
						'$scope',
						'$stateParams',
						'$http',
						'Solicitations',
						function($scope, $stateParams, $http,
								entity, Solicitations) {

							var type;
							var locationUrl;
							var name;
							var Purpose;

							$scope.saveConfirmed = function() {

								name = document.getElementById("documentName").value;
								Purpose = document.getElementById("Purpose").value;
								if (locationUrl == null) {
									locationUrl = document
											.getElementById("field_solicitations").value;
									if (locationUrl == "")
										alert("Please Enter the URL");
								}
								if (Purpose == null) {
									Purpose = "Manuals";
								}
								if (type == null) {
									type = "External Reference";
								}
								$scope.solicitations.requiredDocuments.Manuals
										.push({
											"Name" : name,
											"Type" : type,
											"Purpose" : Purpose,
											"URL" : locationUrl
										});
								$scope.save();
							}

							$scope.uploadFile = function() {
								var fd = new FormData();

								var file = document.getElementById("upload").files[0];

								if (file != null) {
									fd.append('file', file);
									locationUrl = "http://localhost:8080/swagger-ui/";
									console.log(locationUrl);

									var filetype = file.type;

									type = filetype.substring(filetype
											.indexOf("/") + 1);
									console.log(type);

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
									}).then(function s(response) {
										console.log("Post Success");
										locationUrl += response.data.entity;
										$scope.saveConfirmed();
									}, function e(response) {
										console.log("Post failure");
									});
								}

								else
									console.log("no files added");
							};
						} ]);