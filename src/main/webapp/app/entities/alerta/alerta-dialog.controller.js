(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('AlertaDialogController', AlertaDialogController);

    AlertaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Alerta', 'TipoAlerta'];

    function AlertaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Alerta, TipoAlerta) {
        var vm = this;

        vm.alerta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tipoalertas = TipoAlerta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.alerta.id !== null) {
                Alerta.update(vm.alerta, onSaveSuccess, onSaveError);
            } else {
                Alerta.save(vm.alerta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:alertaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
