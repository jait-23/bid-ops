(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('MinimumScoreCriteriaController', MinimumScoreCriteriaController);

    MinimumScoreCriteriaController.$inject = ['$rootScope', '$scope', '$state', '$uibModal', '$http', 'SecondaryEvaluation', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Bidders'];

    function MinimumScoreCriteriaController($rootScope, $scope, $state, $uibModal, $http, SecondaryEvaluation, ParseLinks, AlertService, paginationConstants, pagingParams, Bidders) {

        var vm = this;
        console.log(vm);
    	vm.bidders = Bidders.query();
    	vm.secondaryEvaluation = SecondaryEvaluation.query();
    	console.log(vm.bidders);
    	
    	$scope.score = 0;
    	
    	$rootScope.score = $scope.score;
    	console.log($rootScope.score + "heyy");
    	
    	SecondaryEvaluation.query().$promise
		.then(function(result) {
			console.log("promised SecondaryEvaluation");
			console.log(result);
			$scope.totalCount();
		});
    	console.log(vm.secondaryEvaluation);
    	console.log(vm.bidders);
    	
    	$scope.secondaryEvaluationTotal = [];
		$scope.secondaryEvaluation = [];

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();
        
        $scope.clear = function() {
			$uibModalInstance.dismiss('cancel');
		};

        function loadAll () {
            SecondaryEvaluation.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
                totalAvgScore();
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.secondaryEvaluations = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
        
        $scope.total=0;
        $scope.count=0;
        
        $scope.save = function() {
		
			console.log($scope.score + "m here hey");
			console.log(vm.bidders);
			//vm.bidders[0].minimum_score_for_eligibility.push(vm.score);
			//vm.bidders.["minimum_score_for_eligibility"].push({vm.score});
			vm.bidders[0].minimumScoreForEligibility=$scope.score;
			/* var data = $.param({
				 vm.bidders[0].minimumScoreForEligibility:vm.score;
	            });

	            $http.put('http://localhost:8080/api/bidders'+ vm.score)
	            .success(function (data, status, headers) {
	                $scope.ServerResponse = data;
	            })
	            .error(function (data, status, header, config) {
	                $scope.ServerResponse =  htmlDecode("Data: " + data +
	                    "\n\n\n\nstatus: " + status +
	                    "\n\n\n\nheaders: " + header +
	                    "\n\n\n\nconfig: " + config);
	            }
			*/
			/*$http({
                url: 'http://localhost:8080/api/bidders',
                method : 'POST',
                data:  vm.score,
                withCredentials: true,
                headers: {'Content-Type': undefined },
                transformRequest: angular.identity,
                mimeType:"multipart/form-data",
                contentType: false,
                cache: false,
                processData:false
             }).then(function s(response) 
             {console.log("Post Success"); 
             vm.bidders[0].minimumScoreForEligibility = vm.score;
             Bidders.save(vm.bidders, onSaveFinished);},
             function e(response){console.log("Post failure");});*/
			
			//for(var i=0; i<vm.bidders.length; i++)
			//{
				//vm.bidders[i]["minimum_score_for_eligibility"]=vm.score;
			//	vm.bidders[i].minimum_score_for_eligibility.push(vm.score);
			//}
			
        };
        
       
        
        
       // console.log(totalAvgScore());
        
      $scope.totalCount = function(){
    	  for (var i=0; i < $scope.secondaryEvaluation.length; i++) {
        		if ($scope.secondaryEvaluation[i].score != null &&  $scope.secondaryEvaluation[i].score != undefined && $scope.secondaryEvaluation[i].score != 'null') {
        			$scope.total += $scope.secondaryEvaluation[i].score;
        			console.log($scope.total);
        			console.log($scope.count);
        		}
        	}
      }
        
        /*var average=total/count;
        
        vm.secondaryEvaluations.forEach( function (arrayItem)
            	{
        	if(vm.secondaryEvaluations[i].score>average){
        		vm.secondaryEvaluations[i].eligible="yes";
        		console.log("It was a YES" + vm.secondaryEvaluations[i]);
        		
        	}else{
        		vm.secondaryEvaluations[i].eligible="no";
        		console.log("It was a NO");
        	}
        		});
        for(var i=0; i<vm.secondaryEvaluations.length; i++) {
        	if(vm.secondaryEvaluations[i].score!="null" && vm.secondaryEvaluations[i].score!=undefined)
        		{
        	      total+=vm.secondaryEvaluations[i].score;
        	      console.log(total);
        	      count++;
        	      console.log(count);
        		}
        }
        
        
        var average=total/count;
        for(i=0; i<vm.secondaryEvaluations.length; i++){
        	if(vm.secondaryEvaluations[i].score>average){
        		vm.secondaryEvaluations[i].eligible="yes";
        		console.log("It was a YES" + vm.secondaryEvaluations[i]);
        		
        	}else{
        		vm.secondaryEvaluations[i].eligible="no";
        		console.log("It was a NO");
        	}
        	
	
        } */
      
      $scope.saveDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/secondary-evaluation/secondary-evaluation-details-save-dialog.html',
				controller : 'SecondaryEvaluationDialogDetailsSaveController',
				scope : $scope,
				size : 'lg'
			});
		}
        
        $scope.showFee = function(userId) {
        	
        	
        	console.log(vm.bidders + "hey user");

            $scope.msg = 'clicked';
          } 
    }
})();
