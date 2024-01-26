import {CryptoCurrencyService, TradeService} from 'Frontend/generated/endpoints.js';
import {GridColumn} from "@hilla/react-components/GridColumn";
import {Grid} from "@hilla/react-components/Grid";
import React, {SetStateAction, useEffect, useState} from 'react';
import CryptoCurrency from "Frontend/generated/ch/heigvd/application/data/entities/CryptoCurrency";
import {Notification} from '@hilla/react-components/Notification.js';
import {HorizontalLayout} from '@hilla/react-components/HorizontalLayout.js';
import {NumberField} from "@hilla/react-components/NumberField";
import {RadioGroup} from "@hilla/react-components/RadioGroup";
import {RadioButton} from "@hilla/react-components/RadioButton";
import {Button} from '@hilla/react-components/Button.js';
import {EndpointError} from "@hilla/frontend";

export default function TradeView() {
    const [cryptoCurrencies, setCryptoCurrencies] = useState<CryptoCurrency[]>([]);
    const [selectedItem, setSelectedItem] = useState<CryptoCurrency | null>(null);
    const [usdAmound, setUsdAmound] = useState("0");
    const [cryptoAmount, setCryptoAmount] = useState("0");
    const [tradeAction, setTradeAction] = useState<'buy' | 'sell'>('buy');

    const loadCryptoCurrencies = () => {
        CryptoCurrencyService.getAllWithPrice().then(setCryptoCurrencies);
    }

    const handleUsdAmountChange = (e: any) => {
        const amountInUSD = parseFloat(e.target.value);
        if (selectedItem && amountInUSD) {
            const cryptoPrice = selectedItem.lastPrice;
            const amountInCrypto = amountInUSD / cryptoPrice;
            setCryptoAmount(amountInCrypto.toFixed(8));
        }
        setUsdAmound(e.target.value);
    };

    const handleCryptoAmountChange = (e: any) => {
        const amountInCrypto = parseFloat(e.target.value);
        if (selectedItem && amountInCrypto) {
            const cryptoPrice = selectedItem.lastPrice;
            const amountInUSD = cryptoPrice * amountInCrypto;
            setUsdAmound(amountInUSD.toFixed(2));
        }
        setCryptoAmount(e.target.value);
    };

    useEffect(() => {
        loadCryptoCurrencies();

        const interval = setInterval(() => {
            loadCryptoCurrencies();
        }, 10000);

        return () => clearInterval(interval);
    }, []);

    useEffect(() => {
        if (selectedItem) {
            const updatedItem = cryptoCurrencies.find(crypto => crypto.symbol === selectedItem.symbol);
            if (updatedItem) {
                setSelectedItem(updatedItem);
            }
        }
    }, [cryptoCurrencies]);


    const handleTrade = async () => {
        const amount = parseFloat(cryptoAmount);
        if (selectedItem && selectedItem.symbol && amount > 0) {
            const tradeFunction = tradeAction === 'buy' ? TradeService.buy : TradeService.sell;
            try{
                await tradeFunction(selectedItem.symbol, amount)
                Notification.show(tradeAction === 'buy' ? 'Successfully buy trade' : 'Successfully sell trade', {theme: 'success'});
                setUsdAmound("0");
                setCryptoAmount("0");
            } catch (error) {
                if (error instanceof EndpointError) {
                    Notification.show(`Error during trade: ${error.message}`, {theme: 'error'});
                } else {
                    Notification.show(`Error during trade`, {theme: 'error'});
                }
            }
        }
    }

    const handleBuyClick = () => {
        setTradeAction('buy');
    };

    const handleSellClick = () => {
        setTradeAction('sell');
    };

    const buttonTheme = `primary ${tradeAction === 'buy' ? 'success' : 'error'}`;

    return (
        <>
            <section>
                <div className="p-m flex gap-m">
                    <Grid
                        items={cryptoCurrencies}
                        onActiveItemChanged={(e) => {
                            const item = e.detail.value;
                            setSelectedItem(item || null);
                        }}
                    >
                        <GridColumn path="name"/>
                        <GridColumn path="symbol"/>
                        <GridColumn path="lastPrice"/>
                    </Grid>
                    {selectedItem && (
                        <div>
                            <h3>{selectedItem.name}</h3>
                            <h3>{selectedItem.lastPrice}</h3>
                            <RadioGroup label="Action" value={tradeAction}>
                                <RadioButton label="Buy" value="buy" onClick={handleBuyClick}/>
                                <RadioButton label="Sell" value="sell" onClick={handleSellClick}/>
                            </RadioGroup>
                            <HorizontalLayout theme="spacing">
                                <NumberField label="USD Amount" value={usdAmound.toString()}
                                             onChange={handleUsdAmountChange}>
                                    <div slot="prefix">$</div>
                                </NumberField>
                                <NumberField label={`${selectedItem.symbol} Amount`} value={cryptoAmount} onChange={handleCryptoAmountChange}>
                                    <div slot="prefix">{selectedItem.symbol}</div>
                                </NumberField>
                            </HorizontalLayout>
                            <Button onClick={handleTrade} theme={buttonTheme}>
                                {tradeAction === 'buy' ? 'Buy' : 'Sell'}
                            </Button>
                        </div>
                    )}
                </div>
            </section>
        </>
    );
}
