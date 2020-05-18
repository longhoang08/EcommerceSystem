# coding=utf-8
import logging

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

flash_sales = {
    "type": "nested",
    "properties": {
        "description": {
            "type": "keyword"
        },
        "discount_percent": {
            "type": "double"
        },
        "flash_sale_price": {
            "type": "double"
        },
        "time_ranges": {
            "type": "nested",
            "properties": {
                "end": {
                    "type": "date",
                    "format": "yyyy-MM-dd'T'HH:mm:ss'Z'"
                },
                "start": {
                    "type": "date",
                    "format": "yyyy-MM-dd'T'HH:mm:ss'Z'"
                }
            }
        },
        "id": {
            "type": "integer",
        },
        "is_deleted": {
            "type": "boolean"
        },
        "name": {
            "type": "keyword",
        },
        "total": {
            "type": "integer"
        }
    }
}

stock = {
    "properties": {
        "in_stock": {
            "type": "integer"
        },
        "in_stock_sortable": {
            "type": "integer"
        }
    }
}

prices = {
    "properties": {
        "price_sortable": {
            "type": "double"
        },
        "price": {
            "type": "double"
        },
        "sale_price": {
            "type": "double"
        },
        "import_price": {
            "type": "double"
        }
    }
}

promotions = {
    "properties": {
        "out_of_budget": {
            "type": "boolean"
        },
        "quantity": {
            "type": "integer"
        },
        "discount_percent": {
            "type": "integer"
        },
        "discount_money": {
            "type": "integer"
        }
    }
}

products_ppm = {
    "flash_sales": flash_sales,
    "stock": stock,
    "prices": prices,
    "promotions": promotions
}

categories = {
    "type": "nested",
    "properties": {
        "code": {
            "type": "keyword"
        },
        "id": {
            "type": "integer",
        },
        "level": {
            "type": "integer",
        },
        "name": {
            "type": "text",
        },
        "name_no_tone": {
            "type": "text",
        },
        "parent_id": {
            "type": "integer",
        }
    }
}

basic_details = {
    "sku": {
        "type": "keyword"
    },
    "name": {
        "type": "text",
    },
    "name_no_tone": {
        "type": "keyword",
    },
    "keyword_generated": {
        "type": "boolean"
    },
    "search_text": {
        "type": "text",
        "fields": {
            "raw": {
                "type": "keyword"
            }
        }
    },
    "search_text_no_tone": {
        "type": "text",
        "fields": {
            "raw": {
                "type": "keyword"
            }
        }
    },
    "description": {
        "type": "text"
    },
    "created_at": {
        "type": "date",
        "format": "yyyy-MM-dd'T'HH:mm:ss'Z'"
    },
    "updated_at": {
        "type": "date",
        "format": "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}

rating_details = {
    "quantity": {
        "dynamic": "strict",
        "properties": {
            "last_1_month": {
                "type": "double"
            },
            "last_1_week": {
                "type": "scaled_float",
                "scaling_factor": 100
            },
            "last_2_month": {
                "type": "double"
            },
            "last_2_week": {
                "type": "scaled_float",
                "scaling_factor": 100
            },
            "last_3_day": {
                "type": "double"
            },
            "last_3_week": {
                "type": "scaled_float",
                "scaling_factor": 100
            }
        }
    },
    "rating": {
        "properties": {
            "average_point": {
                "type": "scaled_float",
                "scaling_factor": 10
            },
            "vote_count": {
                "type": "keyword"
            }
        }
    }
}

products_details = {
    **categories,
    **basic_details,
    **rating_details
}

mappings = {
    "dynamic": "strict",
    "properties": {
        **products_details,
        **products_ppm,
    }
}

settings = {
    "index": {
        "max_result_window": 500000,
        "number_of_shards": "1",
        "analysis": {
            "filter": {
                "synonym_filter": {
                    "type": "synonym",
                    "synonyms": []
                }
            },
            "analyzer": {
                "synonym_analyzer": {
                    "filter": [
                        "lowercase",
                        "synonym_filter"
                    ],
                    "tokenizer": "standard"
                }
            }
        }
    }
}
