(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('BiddersDeleteController',BiddersDeleteController);

    BiddersDeleteController.$inject = ['$uibModalInstance', 'entity', 'Bidders'];

    function BiddersDeleteController($uibModalInstance, entity, Bidders) {
        var vm = this;

        vm.bidders = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Bidders.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
