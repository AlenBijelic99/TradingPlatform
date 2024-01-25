import {CryptoCurrencyService, TradeService} from 'Frontend/generated/endpoints.js';
import CryptoCurrencyRecord
    from "Frontend/generated/ch/heigvd/application/services/CryptoCurrencyService/CryptoCurrencyRecord";
import {GridColumn} from "@hilla/react-components/GridColumn";
import {Grid} from "@hilla/react-components/Grid";
import {useEffect, useState} from 'react';
import {Dialog} from '@hilla/react-components/Dialog.js';
import {Button} from "@hilla/react-components/Button.js";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout";
import {NumberField} from "@hilla/react-components/NumberField";
import {Notification} from "@hilla/react-components/Notification";
import {EndpointError} from "@hilla/frontend";

export default function TradeView() {
    const [cryptoCurrencies, setCryptoCurrencies] = useState<CryptoCurrencyRecord[]>([]);
    const [selectedItem, setSelectedItem] = useState<CryptoCurrencyRecord | null>(null);
    const [dialogOpened, setDialogOpened] = useState(false);
    const [selectedAction, setSelectedAction] = useState<'Buy' | 'Sell'>('Buy');
    const [tradeAmount, setTradeAmount] = useState<number>(0);

    const loadCryptoCurrencies = () => {
        CryptoCurrencyService.getAllWithPrice().then(setCryptoCurrencies);
    }

    useEffect(() => {
        loadCryptoCurrencies();

        const interval = setInterval(() => {
            loadCryptoCurrencies();
        }, 10000);

        return () => clearInterval(interval);
    }, []);

    const resetState = () => {
        // Reset state after successful trade
        setDialogOpened(false);
        setSelectedItem(null);
        setSelectedAction('Buy');
        setTradeAmount(0);
    }
    const handleTrade = async () => {

        try {
            if (!selectedItem) {
                //Just to be sure for the compiler that selectedItem is not null. Since
                // the dialog is opened only if selectedItem is not null, this should never happen
                return;
            }
            // Call the TradeService to perform the buy/sell operation
            if (selectedAction === 'Buy') {
                console.log("waiting for TradeService.buy");

                await TradeService.buy(selectedItem.symbol, tradeAmount);
            } else if (selectedAction === 'Sell') {
                console.log("waiting for TradeService.sell");
                await TradeService.sell(selectedItem.symbol, tradeAmount);
            }
            resetState();
        } catch (error) {
            if (error instanceof EndpointError) {
                Notification.show((error as EndpointError).message, {theme: 'error'});
            } else {
                Notification.show("Unexpected error", {theme: 'error'});
            }
        }
    };


    const close = () => {
        resetState();
    }

    return (
        <>
            <section>
                <div className="p-m flex gap-m">
                    <Grid
                        items={cryptoCurrencies}
                        onActiveItemChanged={(e) => {
                            const item = e.detail.value;
                            setSelectedItem(item || null);
                            if (item) {
                                setDialogOpened(true);
                            }
                        }}
                    >
                        <GridColumn path="name"/>
                        <GridColumn path="symbol"/>
                        <GridColumn path="price"/>
                    </Grid>
                </div>
            </section>

            {selectedItem && (
                <Dialog
                    aria-label={`Trade ${selectedItem.name}`}
                    modeless={false}
                    opened={dialogOpened}
                    onOpenedChanged={(event) => setDialogOpened(event.detail.value)}
                    headerRenderer={() => (
                        <h2
                            className="draggable"
                            style={{
                                flex: 1,
                                cursor: 'move',
                                margin: 0,
                                fontSize: '1.5em',
                                fontWeight: 'bold',
                                padding: 'var(--lumo-space-m) 0',
                            }}
                        >
                            {selectedAction === 'Buy' ? `Buy ${selectedItem.name}` : `Sell ${selectedItem.name}`}
                        </h2>
                    )}
                    footerRenderer={() => (
                        <>
                            <Button onClick={handleTrade}>Trade</Button>
                            <Button onClick={close}> Cancel </Button>

                        </>
                    )}
                >
                    <VerticalLayout theme="spacing">
                        <select
                            value={selectedAction || ''}
                            onChange={(event: React.ChangeEvent<HTMLSelectElement>) => setSelectedAction(event.target.value as 'Buy' | 'Sell')}
                        >
                            <option value="Buy">Buy</option>
                            <option value="Sell">Sell</option>
                        </select>

                        <NumberField
                            label="Amount"
                            value={tradeAmount !== null ? tradeAmount.toString() : undefined}
                            onValueChanged={(event) =>
                                setTradeAmount(event.detail.value !== null ? parseFloat(event.detail.value) : 0)
                            }
                        />


                    </VerticalLayout>
                </Dialog>
            )}
        </>
    );
}
