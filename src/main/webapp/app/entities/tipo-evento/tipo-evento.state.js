(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-evento', {
            parent: 'entity',
            url: '/tipo-evento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.tipoEvento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-evento/tipo-eventos.html',
                    controller: 'TipoEventoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoEvento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-evento-detail', {
            parent: 'entity',
            url: '/tipo-evento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.tipoEvento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-evento/tipo-evento-detail.html',
                    controller: 'TipoEventoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoEvento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoEvento', function($stateParams, TipoEvento) {
                    return TipoEvento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-evento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-evento-detail.edit', {
            parent: 'tipo-evento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-evento/tipo-evento-dialog.html',
                    controller: 'TipoEventoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoEvento', function(TipoEvento) {
                            return TipoEvento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-evento.new', {
            parent: 'tipo-evento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-evento/tipo-evento-dialog.html',
                    controller: 'TipoEventoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-evento', null, { reload: 'tipo-evento' });
                }, function() {
                    $state.go('tipo-evento');
                });
            }]
        })
        .state('tipo-evento.edit', {
            parent: 'tipo-evento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-evento/tipo-evento-dialog.html',
                    controller: 'TipoEventoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoEvento', function(TipoEvento) {
                            return TipoEvento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-evento', null, { reload: 'tipo-evento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-evento.delete', {
            parent: 'tipo-evento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-evento/tipo-evento-delete-dialog.html',
                    controller: 'TipoEventoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoEvento', function(TipoEvento) {
                            return TipoEvento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-evento', null, { reload: 'tipo-evento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
