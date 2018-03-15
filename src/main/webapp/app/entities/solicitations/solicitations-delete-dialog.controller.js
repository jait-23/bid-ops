(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationsDeleteController',SolicitationsDeleteController);

    SolicitationsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Solicitations'];

    function SolicitationsDeleteController($uibModalInstance, entity, Solicitations) {
        var vm = this;

        vm.solicitations = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Solicitations.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
