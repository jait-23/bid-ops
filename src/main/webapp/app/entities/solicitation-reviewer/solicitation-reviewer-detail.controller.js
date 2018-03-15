(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationReviewerDetailController', SolicitationReviewerDetailController);

    SolicitationReviewerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SolicitationReviewer'];

    function SolicitationReviewerDetailController($scope, $rootScope, $stateParams, previousState, entity, SolicitationReviewer) {
        var vm = this;

        vm.solicitationReviewer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:solicitationReviewerUpdate', function(event, result) {
            vm.solicitationReviewer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
