(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationReviewerDeleteController',SolicitationReviewerDeleteController);

    SolicitationReviewerDeleteController.$inject = ['$uibModalInstance', 'entity', 'SolicitationReviewer'];

    function SolicitationReviewerDeleteController($uibModalInstance, entity, SolicitationReviewer) {
        var vm = this;

        vm.solicitationReviewer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SolicitationReviewer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
