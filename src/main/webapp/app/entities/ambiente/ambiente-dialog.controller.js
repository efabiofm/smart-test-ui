(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('AmbienteDialogController', AmbienteDialogController);

    AmbienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ambiente', 'Modulo', 'Prueba'];

    function AmbienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ambiente, Modulo, Prueba) {
        var vm = this;

        vm.ambiente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.modulos = Modulo.query();
        vm.pruebas = Prueba.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ambiente.id !== null) {
                Ambiente.update(vm.ambiente, onSaveSuccess, onSaveError);
            } else {
                Ambiente.save(vm.ambiente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:ambienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
