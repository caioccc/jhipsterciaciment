import { element, by, ElementFinder } from 'protractor';

export class PedidoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-pedido div table .btn-danger'));
    title = element.all(by.css('jhi-pedido div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class PedidoUpdatePage {
    pageTitle = element(by.id('jhi-pedido-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    formatoEntregaSelect = element(by.id('field_formatoEntrega'));
    dataInput = element(by.id('field_data'));
    valorUnitarioInput = element(by.id('field_valorUnitario'));
    prazoInput = element(by.id('field_prazo'));
    foiEntregueInput = element(by.id('field_foiEntregue'));
    foiVisualizadoInput = element(by.id('field_foiVisualizado'));
    saiuEntregaInput = element(by.id('field_saiuEntrega'));
    observacoesInput = element(by.id('field_observacoes'));
    clienteSelect = element(by.id('field_cliente'));
    vendedorSelect = element(by.id('field_vendedor'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setFormatoEntregaSelect(formatoEntrega) {
        await this.formatoEntregaSelect.sendKeys(formatoEntrega);
    }

    async getFormatoEntregaSelect() {
        return this.formatoEntregaSelect.element(by.css('option:checked')).getText();
    }

    async formatoEntregaSelectLastOption() {
        await this.formatoEntregaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setDataInput(data) {
        await this.dataInput.sendKeys(data);
    }

    async getDataInput() {
        return this.dataInput.getAttribute('value');
    }

    async setValorUnitarioInput(valorUnitario) {
        await this.valorUnitarioInput.sendKeys(valorUnitario);
    }

    async getValorUnitarioInput() {
        return this.valorUnitarioInput.getAttribute('value');
    }

    async setPrazoInput(prazo) {
        await this.prazoInput.sendKeys(prazo);
    }

    async getPrazoInput() {
        return this.prazoInput.getAttribute('value');
    }

    getFoiEntregueInput() {
        return this.foiEntregueInput;
    }
    getFoiVisualizadoInput() {
        return this.foiVisualizadoInput;
    }
    getSaiuEntregaInput() {
        return this.saiuEntregaInput;
    }
    async setObservacoesInput(observacoes) {
        await this.observacoesInput.sendKeys(observacoes);
    }

    async getObservacoesInput() {
        return this.observacoesInput.getAttribute('value');
    }

    async clienteSelectLastOption() {
        await this.clienteSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async clienteSelectOption(option) {
        await this.clienteSelect.sendKeys(option);
    }

    getClienteSelect(): ElementFinder {
        return this.clienteSelect;
    }

    async getClienteSelectedOption() {
        return this.clienteSelect.element(by.css('option:checked')).getText();
    }

    async vendedorSelectLastOption() {
        await this.vendedorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async vendedorSelectOption(option) {
        await this.vendedorSelect.sendKeys(option);
    }

    getVendedorSelect(): ElementFinder {
        return this.vendedorSelect;
    }

    async getVendedorSelectedOption() {
        return this.vendedorSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class PedidoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-pedido-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-pedido'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
