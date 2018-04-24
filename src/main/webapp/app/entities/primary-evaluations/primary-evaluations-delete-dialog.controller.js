(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('PrimaryEvaluationsDeleteController',PrimaryEvaluationsDeleteController);

    PrimaryEvaluationsDeleteController.$inject = ['$uibModalInstance', 'entity', 'PrimaryEvaluations'];

    function PrimaryEvaluationsDeleteController($uibModalInstance, entity, PrimaryEvaluations) {
        var vm = this;

        vm.primaryEvaluations = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PrimaryEvaluations.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
