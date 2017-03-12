'use strict';

describe('Controller Tests', function() {

    describe('Ambiente Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAmbiente, MockModulo, MockPrueba;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAmbiente = jasmine.createSpy('MockAmbiente');
            MockModulo = jasmine.createSpy('MockModulo');
            MockPrueba = jasmine.createSpy('MockPrueba');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Ambiente': MockAmbiente,
                'Modulo': MockModulo,
                'Prueba': MockPrueba
            };
            createController = function() {
                $injector.get('$controller')("AmbienteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:ambienteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
