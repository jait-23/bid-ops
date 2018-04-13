(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('EvaluatorOneDeleteController',EvaluatorOneDeleteController);

    EvaluatorOneDeleteController.$inject = ['$uibModalInstance', 'entity', 'EvaluatorOne'];

    function EvaluatorOneDeleteController($uibModalInstance, entity, EvaluatorOne) {
        var vm = this;

        vm.evaluatorOne = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EvaluatorOne.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
