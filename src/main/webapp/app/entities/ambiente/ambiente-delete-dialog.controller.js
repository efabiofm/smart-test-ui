(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('AmbienteDeleteController',AmbienteDeleteController);

    AmbienteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ambiente'];

    function AmbienteDeleteController($uibModalInstance, entity, Ambiente) {
        var vm = this;

        vm.ambiente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ambiente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
